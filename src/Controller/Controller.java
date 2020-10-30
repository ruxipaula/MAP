package Controller;

import Exceptions.MyException;
import Model.PrgState;
import Model.Value.RefValue;
import Model.Value.Value;
import Repository.IRepository;
import Repository.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository r) {
        repo = r;
    }

    public IRepository getRepo() { return repo;}

    Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues, Collection<Value> heapValues) {
        return Stream.concat(
                heapValues.stream()
                    .filter(v->v instanceof RefValue)
                    .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();}),
                symTableValues.stream()
                    .filter(v->v instanceof RefValue)
                    .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
        ).collect(Collectors.toList());
    }

    private void conservativeGarbageCollector(List<PrgState> prgList) {
        prgList.forEach(p -> p.getHeap().setContent(safeGarbageCollector(getAddrFromSymTable(p.getSymTable().getContent().values(),
                p.getHeap().getContent().values()), p.getHeap().getContent()))
        );
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws MyException{
        //before the execution, print the PrgSTate List into log file

        //run concurrently one step for each of the existing PrgStates
        // --
        //prepare list of callables
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());

        //start the execution of the callables
        //it returns the list of new created PrgStates (namely threads)

        List<PrgState> newPrgList = null;

        try {
            newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> { try {return future.get();}
                                    catch (InterruptedException | ExecutionException ignored) {
                                        return null;}
                                    })
                    .filter(p -> p!=null)
                    .collect(Collectors.toList());
        }catch (InterruptedException e) {
            throw new MyException(e.getMessage());
        }

        //add the new created threads to the list of existing threads
        prgList.addAll(newPrgList);
        //--

        //after the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException ignored) {
            }
        });
        //save the current programs in the repository
        repo.setPrgList(prgList);

    }


    public void allSteps() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while(prgList.size() > 0) {
            conservativeGarbageCollector(prgList);
            oneStepForAllPrg(prgList);
            //remove the completed programs
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        //Here the repo still contains at least one CompletedPrg
        //and its List<PrgState> is not empty. Note that oneStepForAllPrg calls
        //the method setPrgList of repository in order to change the repo

        //update the repository state
        repo.setPrgList(prgList);

    }

    public void oneStep() throws MyException {
        //last version of garbage collector (works for fork stmt)

        executor = Executors.newFixedThreadPool(2);
        List<PrgState> list = removeCompletedPrg(repo.getPrgList());
        if (!list.isEmpty()) {
            conservativeGarbageCollector(repo.getPrgList());
            oneStepForAllPrg(list);
        }
        executor.shutdownNow();
        repo.setPrgList(list);
    }


}

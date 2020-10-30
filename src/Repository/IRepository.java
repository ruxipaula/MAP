package Repository;

import Exceptions.MyException;
import Model.PrgState;

import java.util.List;

public interface IRepository {
    PrgState getCrtPrg();
    void logPrgStateExec(PrgState state) throws MyException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgs);
    public PrgState getPrgState(int id);
}

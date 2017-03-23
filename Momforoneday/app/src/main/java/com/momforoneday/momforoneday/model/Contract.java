package com.momforoneday.momforoneday.model;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class Contract {

    private ContractStatus _status;
    private Caregiver _caregiver;
    private User _user;

    public Contract(Caregiver _caregiver, User _user) {
        this._caregiver = _caregiver;
        this._user = _user;
        this._status = ContractStatus.CONTRACT_PENDING;
    }

    public Caregiver getCaregiver() {
        return _caregiver;
    }

    public void setCaregiver(Caregiver _caregiver) {
        this._caregiver = _caregiver;
    }

    public User getUser() {
        return _user;
    }

    public void setUser(User _user) {
        this._user = _user;
    }

    public String getStatus(){

        if (_status == ContractStatus.CONTRACT_PENDING){
            return "Pendente";
        } else if (_status == ContractStatus.CONTRACT_FINISHED) {
            return "Finalizado";
        } else if (_status == ContractStatus.CONTRACT_REJECTED) {
            return "Rejeitado";
        } else if (_status == ContractStatus.CONTRACT_RUNNING) {
            return "Em andamento";
        }

        return "";
    }

    public void setStatus(ContractStatus newStatus) {
        this._status = newStatus;
    }


}

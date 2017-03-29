package com.momforoneday.momforoneday.model;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class Contract {

    private ContractStatus status;
    private Caregiver caregiver;
    private User user;

    public Contract(){}

    public Contract(Caregiver _caregiver, User _user) {
        this.caregiver = _caregiver;
        this.user = _user;
        this.status = ContractStatus.CONTRACT_PENDING;
    }

    public Caregiver getCaregiver() {
        return caregiver;
    }

    public void setCaregiver(Caregiver _caregiver) {
        this.caregiver = _caregiver;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User _user) {
        this.user = _user;
    }

    public String getStatus(){

        if (status == ContractStatus.CONTRACT_PENDING){
            return "Pendente";
        } else if (status == ContractStatus.CONTRACT_FINISHED) {
            return "Finalizado";
        } else if (status == ContractStatus.CONTRACT_REJECTED) {
            return "Rejeitado";
        } else if (status == ContractStatus.CONTRACT_RUNNING) {
            return "Em andamento";
        }

        return "";
    }

    public void setStatus(ContractStatus newStatus) {
        this.status = newStatus;
    }


}

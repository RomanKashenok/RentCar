package com.kashenok.rentcar.command;

import com.kashenok.rentcar.command.admin.*;
import com.kashenok.rentcar.command.user.*;
import com.kashenok.rentcar.command.navigation.*;

/**
 * The Enum CommandEnum. Contains all command objects.
 */
public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    PROFILECHANGE {
        {
            this.command = new UserProfileChangeCommand();
        }
    },
    PROFILECONFIRMCHANGE {
        {
            this.command = new UserProfileConfirmChangeCommand();
        }
    },
    NEEDREGISTRATION {
        {
            this.command = new RegistrationNeedCommand();
        }
    },
    REGISTRATION {
        {
            this.command = new RegistrationCommand();
        }
    },
    CHANGELANG {
        {
            this.command = new LanguageChangeCommand();
        }
    },
    ORDERNEEDCREATE {
        {
            this.command = new OrderNeedCreateCommand();
        }
    },
    ORDERCREATE {
        {
            this.command = new OrderCreateCommand();
        }
    },
    ORDERCONFIRM {
        {
            this.command = new OrderConfirmCommand();
        }
    },
    ORDERCANCEL {
        {
            this.command = new OrderCancelCommand();
        }
    },
    NEEDREFILLBALANCE {
        {
            this.command = new UserNeedRefillBalanceCommand();
        }
    },
    REFILLBALANCE {
        {
            this.command = new UserRefillBalanceCommand();
        }
    },
    SHOWUSERORDERLIST {
        {
            this.command = new UserShowOrderListCommand();
        }
    },
    SHOWACCEPTEDORDERS {
        {
            this.command = new UserShowAcceptedOrdersCommand();
        }
    },
    SHOWCANCELEDORDERS {
        {
            this.command = new UserShowCanceledOrdersCommand();
        }
    },
    SHOWUSERORDERSARCHIVE {
        {
            this.command = new UserShowArchiveOrdersCommand();
        }
    },
    ORDERNEEDCHANGE {
        {
            this.command = new OrderNeedChangeCommand();
        }
    },
    ORDERCHANGE {
        {
            this.command = new OrderChangeCommand();
        }
    },
    ORDERCHANGECONFIRM {
        {
            this.command = new OrderChangeConfirmCommand();
        }
    },
    ORDERNEEDDELETE {
        {
            this.command = new OrderNeedDeleteCommand();
        }
    },
    ORDERDELETECONFIRM {
        {
            this.command = new OrderDeleteConfirmCommand();
        }
    },
    GOTOMAIN {
        {
            this.command = new GoToMainCommand();
        }
    },
    GOBACK {
        {
            this.command = new GoBackCommand();
        }
    },
    BLOCKUSER {
        {
            this.command = new UserBlockCommand();
        }
    },
    SHOWUSERORDERS {
        {
            this.command = new ShowUserOrdersCommand();
        }
    },
    ADMINCONFIRMORDER {
        {
            this.command = new AdminConfirmOrderCommand();
        }
    },
    ADMINCANCELORDER {
        {
            this.command = new AdminCancelOrderCommand();
        }
    },
    CONFIRMORDERCANCEL {
        {
            this.command = new ConfirmOrderCancelCommand();
        }
    },
    ADMINMAINCONFIRMORDER {
        {
            this.command = new AdminMainConfirmOrderCommand();
        }
    },
    ADMINMAINCANCELORDER {
        {
            this.command = new AdminMainCancelOrderCommand();
        }
    },
    PENALTYFINISHEDORDER {
        {
            this.command = new AdminPenaltySetCommand();
        }
    },
    CONFIRMPENALTY {
        {
            this.command = new AdminPenaltyConfirmCommand();
        }
    },
    PENALTYPAY {
        {
            this.command = new PenaltyPayCommand();
        }
    },
    SHOWCARS {
        {
            this.command = new AdminShowCarsCommand();
        }
    },
    CARNEEDCHANGE {
        {
            this.command = new CarNeedChangeCommand();
        }
    },
    CARCHANGE {
        {
            this.command = new CarChangeCommand();
        }
    },
    ADDNEWCAR {
        {
            this.command = new AddNewCarCommand();
        }
    },
    CONFIRMNEWCAR {
        {
            this.command = new ConfirmNewCarCommand();
        }
    },
    SHOWPENALTY {
        {
            this.command = new ShowPenaltyCommand();
        }
    },
    SHOWPENALTYBYSTATUS {
        {
            this.command = new ShowPenaltyByStatusCommand();
        }
    },
    NEEDCHANGEPENALTY {
        {
            this.command = new NeedChangePenaltyCommand();
        }
    },
    PENALTYCHANGE {
        {
            this.command = new ConfirmChangePenaltyCommand();
        }
    },
    SHOWORDERS {
        {
            this.command = new AdminShowOrdersCommand();
        }
    },
    SHOWORDERBYSTATUS {
        {
            this.command = new AdminShowOrdersByStatusCommand();
        }
    },
    ABOUTUS {
        {
            this.command = new ShowAboutUsCommand();
        }
    },
    ACCEPTFINISHEDORDER {
        {
            this.command = new AcceptFinishedOrderCommand();
        }
    };

    ICommand command;

    public ICommand getCurrentCommand() {
        return command;
    }

}

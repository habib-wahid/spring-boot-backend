package com.usb.pss.ipaservice.utils;

import com.usb.pss.ipaservice.admin.model.entity.IpaAdminAction;
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;

public class DaprUtils {
    private static final String DAPR_SATE_STORE = "ipa-state-store";
    public static IpaAdminAction getUserActionFromDapr(Long actionId){
        DaprClient daprClient = new DaprClientBuilder().build();
        return daprClient.getState(DAPR_SATE_STORE,String.valueOf(actionId),IpaAdminAction.class).block().getValue();
    }

    public static void saveUserActionInDapr(Long actionId, IpaAdminAction ipaAdminAction){
        DaprClient daprClient = new DaprClientBuilder().build();
        daprClient.saveState(DAPR_SATE_STORE,String.valueOf(actionId),ipaAdminAction).block();
    }

    public static void deleteUserActionFromDapr(Long actionId){
        DaprClient daprClient = new DaprClientBuilder().build();
        daprClient.deleteState(DAPR_SATE_STORE,String.valueOf(actionId)).block();
    }
}

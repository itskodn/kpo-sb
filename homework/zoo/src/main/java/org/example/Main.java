package org.example;

import org.example.ui.MenuHandler;
import org.example.container.DIContainer;
import org.example.services.VeterinaryClinic;
import org.example.services.InventoryService;
import org.example.services.ZooService;

public class Main {
    public static void main(String[] args) {
        if (isTestMode(args)) {
            return;
        }

        DIContainer container = new DIContainer();

        MenuHandler menuHandler = new MenuHandler(
                container.resolve(ZooService.class),
                container.resolve(InventoryService.class),
                container.resolve(VeterinaryClinic.class)
        );
        menuHandler.showMainMenu();
    }

    private static boolean isTestMode(String[] args) {
        for (String arg : args) {
            if ("test".equals(arg)) {
                return true;
            }
        }
        return false;
    }
}
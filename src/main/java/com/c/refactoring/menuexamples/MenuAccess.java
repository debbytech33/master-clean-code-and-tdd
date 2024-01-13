package com.c.refactoring.menuexamples;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MenuAccess {

    public void setAuthorizationsInEachMenus(List<MenuItem> menuItems, Role[] roles) {

        if (roles == null)
            return;

        if (menuItems != null) {
            menuItems.stream().forEach(menuItem -> setAccessForMenuItem(roles, menuItem));
        }

    }

    private static void setAccessForMenuItem(Role[] roles, MenuItem menuItem) {
        if (doesUserHaveTheRole(roles, menuItem.getReadAccessRole())) {
            setMenuItemAuthorizations(menuItem, Constants.READ);
        }

        if (doesUserHaveTheRole(roles, menuItem.getWriteAccessRole())) {
            setMenuItemAuthorizations(menuItem, Constants.WRITE);
        }
    }

    private static void setMenuItemAuthorizations(MenuItem menuItem, String read) {
        menuItem.setAccess(read);
        menuItem.setVisible(true);
    }

    private static boolean doesUserHaveTheRole(Role[] roles, String roleToCheckFor) {
        return Arrays.stream(roles).anyMatch(role -> role.getName().equals(roleToCheckFor));
    }

}

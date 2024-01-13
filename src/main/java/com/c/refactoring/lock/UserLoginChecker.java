package com.c.refactoring.lock;

import java.util.Date;
import java.util.List;

public class UserLoginChecker {

    private static final int MAXIMUM_LOCK_PERIOD_IN_MS = 60 * 60 * 1000;

    /**
     * {@inheritDoc}.
     */
    public Lock isUserAllowedToLogin(long id, String status,
            boolean isFirstScreen, User userTryingToLogin, List existingLocks) {

        if (existingLocks.size() == 0 || existingLocks.get(0) == null) {
            return createWriteLock();
        }

        Object[] existingLock = (Object[]) existingLocks.get(0);
        String userIdWithLock = (String) existingLock[0];
        Date lockTimestamp = (Date) existingLock[1];

        if (userIdWithLock == null) {
            return createWriteLock();
        }

        if (userIdWithLock.equalsIgnoreCase(userTryingToLogin.getUserId())) {
            return createWriteLock();
        }

        long timeElapsedSinceLock = new Date().getTime() - lockTimestamp.getTime();
        if (isFirstScreen && timeElapsedSinceLock > MAXIMUM_LOCK_PERIOD_IN_MS) {
            return createWriteLock();
        }

        return createReadLockWithMessage(userIdWithLock);
    }

    private static Lock createReadLockWithMessage(String userIdWithLock) {
        String lockMsg = Constants.LOCK_TEXT.replaceAll("@@USER@@",
                userIdWithLock);
        //Only read access is permitted to other user
        Lock lock = new Lock();
        lock.setRead(true);
        lock.setLockReason(lockMsg);
        return lock;
    }

    private static Lock createWriteLock() {
        Lock lock = new Lock();
        lock.setRead(false);
        return lock;
    }


    public Lock isUserAllowedToLogin_MyRefactoredVersion(long id, String status,
                                     boolean firstScreen, User userToCheckFor, List users) {

        Lock lck = new Lock();

        if (users == null || users.size() > 0 && users.get(0) == null)
          return lck;

        String userId = (String) ((Object[]) users.get(0))[0];

        if (userId== null)
            return lck;

        Date lockTimestamp = (Date) ((Object[]) users.get(0))[1];

        String lockMsg = Constants.LOCK_TEXT.replaceAll("@@USER@@",
                userId);
        //if userID is present, the Lock time stamp will also be present
        //4800000 milliseconds equals to 1 1/2 hours.
        if (new Date().getTime() - lockTimestamp.getTime() > MAXIMUM_LOCK_PERIOD_IN_MS) {
            //New user gets lock only on first screen
            //If 1 1/2 hours expires when user is not on 1st screen then for same user lock can be refreshed.
            if (firstScreen
                    || userId.equalsIgnoreCase(userToCheckFor.getUserId())) {
                //to set the  access to write mode
                return setAccessToUser(lck, null, false);
            }
            //Only read access is permitted to other user
            return setAccessToUser(lck, lockMsg, true);
        }

        if (!userId.equalsIgnoreCase(userToCheckFor.getUserId())) {
            //Only Read Access is Permitted
            return setAccessToUser(lck, lockMsg, true);
        }
        return lck;
    }


    private static Lock setAccessToUser(Lock lck, String lockMsg, boolean readAccess) {
        lck.setRead(readAccess);
        lck.setLockReason(lockMsg);
        return lck;
    }

}
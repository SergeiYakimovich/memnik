package com.memnik.common;

import com.memnik.service.common.NotificationService;
import com.memnik.service.user.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.memnik.common.constants.Constants.*;
import static net.javacrumbs.shedlock.core.LockAssert.assertLocked;

@Slf4j
@Component
public class ShedulerTasks {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private NotificationService notificationService;

    @Scheduled(fixedDelayString = DELETE_UNCONFIRMED_FIXED_DELAY, initialDelayString = DELETE_UNCONFIRMED_INIT_DELAY)
    @SchedulerLock(name = "deleteUnconfirmedUsers",
            lockAtLeastFor = LOCK_AT_LEAST_FOR,
            lockAtMostFor = LOCK_AT_MOST_FOR)
    public void deleteUnconfirmedUsers() {
        assertLocked();
        log.info("Start delete unconfirmed users");
        userDetailsService.deleteUnconfirmedUsers();
        log.info("End delete unconfirmed users");
    }

    @Scheduled(fixedDelayString = NOTIFY_USERS_FIXED_DELAY, initialDelayString = NOTIFY_USERS_INIT_DELAY)
    @SchedulerLock(name = "notifyUsers",
            lockAtLeastFor = LOCK_AT_LEAST_FOR,
            lockAtMostFor = LOCK_AT_MOST_FOR)
    public void notifyUsers() {
        assertLocked();
        log.info("Start notify users");
        notificationService.notifyUsers();
        log.info("End notify users");
    }
}

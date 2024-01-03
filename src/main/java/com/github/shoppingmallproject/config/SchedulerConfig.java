package com.github.shoppingmallproject.config;

import com.github.shoppingmallproject.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerConfig {
    private final ScheduleService scheduleService;

//    @Scheduled(cron = "1 0 0 * * *")
    @Scheduled(cron = "1 0 0 * * *")
    public void cleanupOldWithdrawnUserAndSetupOldProduct(){
        scheduleService.cleanupOldWithdrawnUser();
        log.info("탈퇴한지 7일 이상된 계정을 삭제 하였습니다.");
        scheduleService.setupOldProduct();
        log.info("판매 종료일자 확인후 판매상태를 변경 하였습니다.");
    }

}

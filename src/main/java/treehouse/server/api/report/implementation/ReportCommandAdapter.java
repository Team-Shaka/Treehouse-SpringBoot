package treehouse.server.api.report.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import treehouse.server.api.report.persistent.ReportRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.report.Report;

@Adapter
@RequiredArgsConstructor
@Slf4j
public class ReportCommandAdapter {

    private final ReportRepository reportRepository;

    public Report createReport(Report report){
        return reportRepository.save(report);
    }
}

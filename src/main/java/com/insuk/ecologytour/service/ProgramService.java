package com.insuk.ecologytour.service;

import com.insuk.ecologytour.common.ConstUtil;
import com.insuk.ecologytour.common.StringUtil;
import com.insuk.ecologytour.domain.entity.Program;
import com.insuk.ecologytour.web.request.ModifyTourDataReq;
import com.insuk.ecologytour.web.request.TourDataReq;
import com.insuk.ecologytour.repository.ProgramRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ProgramService {

    @Autowired
    private ProgramRepository programRepository;

    public Program save(Program program){
        final Optional<Program> savedProgram = programRepository.findByProgramName(program.getProgramName());
        return savedProgram.orElseGet(() -> programRepository.save(program));
    }

    public Program saveByUpload(TourDataReq tourData){
        Program program = Program.builder()
                .programCode(ConstUtil.PROGRAM_CODE_PREFIX + StringUtil.getUUIDNumber())
                .programName(tourData.getProgramName()).build();
        return this.save(program);
    }

    public Program modifyProgram(ModifyTourDataReq modifyTourDataReq) {
        Program program = Program.builder()
                .programCode(ConstUtil.PROGRAM_CODE_PREFIX + StringUtil.getUUIDNumber())
                .programName(modifyTourDataReq.getProgramName()).build();
        return this.save(program);
    }
}

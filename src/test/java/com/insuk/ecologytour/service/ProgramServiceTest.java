package com.insuk.ecologytour.service;

import com.insuk.ecologytour.common.ConstUtil;
import com.insuk.ecologytour.common.StringUtil;
import com.insuk.ecologytour.domain.entity.Program;
import com.insuk.ecologytour.repository.ProgramRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProgramServiceTest {

    @Autowired
    private ProgramService programService;

    @MockBean
    private ProgramRepository programRepository;

    private final String programName = "구로 꽃 축제";

    @Before
    public void setUp(){
        Program program = Program.builder()
                .programCode(ConstUtil.PROGRAM_CODE_PREFIX + StringUtil.getUUIDNumber())
                .programName(programName)
                .build();

        Mockito.when(programRepository.findByProgramName(program.getProgramName()))
                .thenReturn(java.util.Optional.of(program));
    }

    @Test
    public void 프로그램_저장_서비스_테스트(){
        Program savedProgram = programService.save(
                new Program(ConstUtil.PROGRAM_CODE_PREFIX + StringUtil.getUUIDNumber(),
                        programName)
        );
        assertThat(savedProgram.getProgramName(), is(programName));
    }
}
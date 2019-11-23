package com.insuk.ecologytour.repository;

import com.insuk.ecologytour.common.ConstUtil;
import com.insuk.ecologytour.common.StringUtil;
import com.insuk.ecologytour.domain.entity.Program;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProgramRepositoryTest {
    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void 프로그램_저장_조회_테스트(){
        Program program1 = Program.builder()
                .programCode(ConstUtil.PROGRAM_CODE_PREFIX + StringUtil.getUUIDNumber())
                .programName("함께 놀아요 1")
                .build();
        testEntityManager.persist(program1);

        Program program2 = Program.builder()
                .programCode(ConstUtil.PROGRAM_CODE_PREFIX + StringUtil.getUUIDNumber())
                .programName("함께 놀아요 2")
                .build();
        testEntityManager.persist(program2);

        List<Program> programList = programRepository.findAll();

        assertThat(programList, hasSize(2));
        assertThat(programList, contains(program1, program2));
    }

}
package com.scnu.mapper.bus;

import com.scnu.entity.bus.PetHealthRecord;
import java.util.List;

public interface PetHealthRecordMapper {

    int insert(PetHealthRecord petHealthRecord);

    int deleteById(Integer id);

    int updateById(PetHealthRecord petHealthRecord);

    PetHealthRecord selectById(Integer id);

    List<PetHealthRecord> selectAll(PetHealthRecord petHealthRecord);

    List<PetHealthRecord> selectMyPage(PetHealthRecord petHealthRecord);



}
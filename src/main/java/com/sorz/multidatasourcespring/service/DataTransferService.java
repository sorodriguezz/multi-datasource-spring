package com.sorz.multidatasourcespring.service;

import com.sorz.multidatasourcespring.model.reading.ReadingEntity;
import com.sorz.multidatasourcespring.model.writing.WritingEntity;
import com.sorz.multidatasourcespring.repository.reading.ReadingRepository;
import com.sorz.multidatasourcespring.repository.writing.WritingRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DataTransferService {
    private static final Logger logger = LoggerFactory.getLogger(DataTransferService.class);

    private final ReadingRepository readingRepository;
    private final WritingRepository writingRepository;

    public DataTransferService(ReadingRepository readingRepository, WritingRepository writingRepository) {
        this.readingRepository = readingRepository;
        this.writingRepository = writingRepository;
    }

    @Transactional("readingTransactionManager")
    public List<ReadingEntity> getAllReadingData() {
        return readingRepository.findAll();
    }

    @Transactional("writingTransactionManager")
    public List<WritingEntity> getAllWritingData() {
        return writingRepository.findAll();
    }

    @Transactional("writingTransactionManager")
    public WritingEntity saveWritingData(WritingEntity entity) {
        return writingRepository.save(entity);
    }

    @Transactional("readingTransactionManager")
    public ReadingEntity saveReadingData(ReadingEntity entity) {
        return readingRepository.save(entity);
    }

    /**
     * Sincroniza todos los datos desde Writing a Reading, si se vuelve a ejecutar los duplica.
     */
    public void transferData() {
        logger.info("Iniciando transferencia de datos...");
        List<WritingEntity> dataList = getAllWritingData();
        logger.info("Registros encontrados en lectura: {}", dataList.size());
        for (WritingEntity we : dataList) {
            ReadingEntity re = new ReadingEntity();
            re.setData(we.getInfo());
            ReadingEntity saved = saveReadingData(re);
            logger.info("Transferido registro: {} -> {}", we.getId(), saved.getId());
        }
        logger.info("Transferencia completada.");
    }
}

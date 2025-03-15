package com.sorz.multidatasourcespring.controller;

import com.sorz.multidatasourcespring.model.reading.ReadingEntity;
import com.sorz.multidatasourcespring.model.writing.WritingEntity;
import com.sorz.multidatasourcespring.service.DataTransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class DataTransferController {
    private final DataTransferService dataTransferService;

    public DataTransferController(DataTransferService dataTransferService) {
        this.dataTransferService = dataTransferService;
    }

    // Endpoint para obtener todos los registros de la base de datos de lectura (MySQL)
    @GetMapping("readings")
    public ResponseEntity<List<ReadingEntity>> getAllReadingData() {
        List<ReadingEntity> data = dataTransferService.getAllReadingData();
        return ResponseEntity.ok(data);
    }

    // Endpoint para guardar un registro en la base de datos de escritura (PostgreSQL)
    @PostMapping("writings")
    public ResponseEntity<WritingEntity> saveWritingData(@RequestBody WritingEntity writingEntity) {
        WritingEntity saved = dataTransferService.saveWritingData(writingEntity);
        return ResponseEntity.ok(saved);
    }

    // Endpoint para transferir datos de lectura a escritura
    @PostMapping("transfer")
    public ResponseEntity<String> transferData() {
        dataTransferService.transferData();
        return ResponseEntity.ok("Transferencia completada exitosamente.");
    }
}

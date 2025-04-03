package ru.otus.java.pro.mt.core.transfers.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.java.pro.mt.core.transfers.dtos.ExecuteTransferDtoRq;
import ru.otus.java.pro.mt.core.transfers.entities.Transfer;

import java.util.List;
import java.util.Optional;

public interface TransfersService {
    Optional<Transfer> getTransferById(String id, String clientId);
    List<Transfer> getAllTransfers(String clientId);
    Page<Transfer> getAllTransfers(String clientId, Pageable pageable);
    void execute(String clientId, ExecuteTransferDtoRq executeTransferDtoRq);
    void save(Transfer transfer);
}

package ru.otus.java.pro.mt.core.transfers.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.mt.core.transfers.configs.properties.TransfersProperties;
import ru.otus.java.pro.mt.core.transfers.dtos.ExecuteTransferDtoRq;
import ru.otus.java.pro.mt.core.transfers.entities.Transfer;
import ru.otus.java.pro.mt.core.transfers.exceptions_handling.BusinessLogicException;
import ru.otus.java.pro.mt.core.transfers.metrics.CustomMetricsService;
import ru.otus.java.pro.mt.core.transfers.repositories.TransfersRepository;
import ru.otus.java.pro.mt.core.transfers.validators.TransferRequestValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransfersServiceImpl implements TransfersService {
    private final TransfersRepository transfersRepository;
    private final TransferRequestValidator transferRequestValidator;
    private final TransfersProperties transfersProperties;
    private final LimitsServiceImpl limitsService;
    private final CustomMetricsService customMetricsService;

    @Override
    public Optional<Transfer> getTransferById(String id, String clientId) {
        return transfersRepository.findByIdAndClientId(id, clientId);
    }

    @Override
    public List<Transfer> getAllTransfers(String clientId) {
        return transfersRepository.findAllByClientId(clientId);
    }

    @Override
    public Page<Transfer> getAllTransfers(String clientId, Pageable pageable) {
        return transfersRepository.findAllByClientId(clientId, pageable);
    }

    @Override
    public void execute(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        transferRequestValidator.validate(executeTransferDtoRq);
        // execution
        if (!limitsService.isLimitEnough()) {
            customMetricsService.incrementFailedTransfers();
            throw new BusinessLogicException("Limit not enough", "LIMIT_NOT_ENOUGH");
        }
        if (executeTransferDtoRq.getAmount().compareTo(transfersProperties.getMaxTransferSum()) > 0) {
            customMetricsService.incrementFailedTransfers();
            throw new BusinessLogicException("Amount exceeds maximum limit", "AMOUNT_EXCEEDS_LIMIT");
        }
        Transfer transfer = new Transfer(UUID.randomUUID().toString(), "1", "2", "1", "2", "Demo", BigDecimal.ONE);
        save(transfer);
        customMetricsService.incrementSuccessfulTransfers();
    }

    @Override
    public void save(Transfer transfer) {
        transfersRepository.save(transfer);
    }
}

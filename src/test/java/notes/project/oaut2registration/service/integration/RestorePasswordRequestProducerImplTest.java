package notes.project.oaut2registration.service.integration;

import notes.project.oaut2registration.model.RestorePasswordStruct;
import notes.project.oaut2registration.service.integration.impl.RestorePasswordRequestProducerImpl;
import notes.project.oaut2registration.utils.DbUtils;
import notes.project.oaut2registration.utils.mapper.SendRestorePasswordRequestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static notes.project.oaut2registration.utils.TestDataConstants.EMAIL;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class RestorePasswordRequestProducerImplTest {
    @Mock
    private RestorePasswordRequestPublisher publisher;

    private RestorePasswordRequestProducer producer;

    @BeforeEach
    void init() {
        producer = new RestorePasswordRequestProducerImpl(
            publisher,
            Mappers.getMapper(SendRestorePasswordRequestMapper.class)
        );
    }

    @Test
    void produceMessageSuccess() {
        RestorePasswordStruct struct = DbUtils.restorePasswordStruct();

        assertDoesNotThrow(() -> producer.produceMessage(struct, EMAIL));
    }
}

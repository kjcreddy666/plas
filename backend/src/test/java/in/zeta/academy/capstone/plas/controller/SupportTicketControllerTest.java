package in.zeta.academy.capstone.plas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.zeta.academy.capstone.plas.dto.TicketRequestDto;
import in.zeta.academy.capstone.plas.dto.TicketResponseDto;
import in.zeta.academy.capstone.plas.exception.GlobalExceptionHandler;
import in.zeta.academy.capstone.plas.exception.TicketNotFoundException;
import in.zeta.academy.capstone.plas.service.SupportTicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SupportTicketControllerTest {

    @Mock
    private SupportTicketService supportTicketService;

    @InjectMocks
    private SupportTicketController supportTicketController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(supportTicketController)
                .setControllerAdvice(new GlobalExceptionHandler()) // Include the GlobalExceptionHandler
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createTicket_shouldReturnCreatedTicket() throws Exception {
        TicketRequestDto requestDto = new TicketRequestDto();
        requestDto.setUserId(UUID.randomUUID());
        requestDto.setLoanApplicationId(1L);
        requestDto.setSubject("Test Subject");
        requestDto.setDescription("Test Description");

        TicketResponseDto responseDto = new TicketResponseDto();
        responseDto.setId(1L);
        responseDto.setSubject("Test Subject");

        when(supportTicketService.createTicket(any())).thenReturn(responseDto);

        mockMvc.perform(post("/support/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.subject").value("Test Subject"));

        verify(supportTicketService, times(1)).createTicket(any());
    }

    @Test
    void getTicketByUserId_shouldReturnTicketList() throws Exception {
        UUID userId = UUID.randomUUID();

        TicketResponseDto responseDto = new TicketResponseDto();
        responseDto.setId(1L);
        responseDto.setSubject("Test Subject");

        when(supportTicketService.getTicketDetailsByUserId(userId)).thenReturn(List.of(responseDto));

        mockMvc.perform(get("/support/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].subject").value("Test Subject"));

        verify(supportTicketService, times(1)).getTicketDetailsByUserId(userId);
    }

    @Test
    void getTicketById_shouldReturnTicket() throws Exception {
        Long ticketId = 1L;

        TicketResponseDto responseDto = new TicketResponseDto();
        responseDto.setId(ticketId);
        responseDto.setSubject("Test Subject");

        when(supportTicketService.getTicketDetailsByTicketId(ticketId)).thenReturn(responseDto);

        mockMvc.perform(get("/support/ticket/{ticketId}", ticketId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ticketId))
                .andExpect(jsonPath("$.subject").value("Test Subject"));

        verify(supportTicketService, times(1)).getTicketDetailsByTicketId(ticketId);
    }

    @Test
    void getTicketByUserId_shouldReturnNotFoundIfNoTickets() throws Exception {
        UUID userId = UUID.randomUUID();

        when(supportTicketService.getTicketDetailsByUserId(userId)).thenThrow(new TicketNotFoundException("No tickets found"));

        mockMvc.perform(get("/support/user/{userId}", userId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("No tickets found"))
                .andExpect(jsonPath("$.status").value(404));

        verify(supportTicketService, times(1)).getTicketDetailsByUserId(userId);
    }

    @Test
    void getTicketById_shouldReturnNotFoundIfTicketDoesNotExist() throws Exception {
        Long ticketId = 1L;

        when(supportTicketService.getTicketDetailsByTicketId(ticketId)).thenThrow(new TicketNotFoundException("Ticket not found"));

        mockMvc.perform(get("/support/ticket/{ticketId}", ticketId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Ticket not found"))
                .andExpect(jsonPath("$.status").value(404));

        verify(supportTicketService, times(1)).getTicketDetailsByTicketId(ticketId);
    }
}
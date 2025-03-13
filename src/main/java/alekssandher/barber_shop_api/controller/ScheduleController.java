package alekssandher.barber_shop_api.controller;

import static java.time.ZoneOffset.UTC;

import java.time.YearMonth;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alekssandher.barber_shop_api.dto.response.ApiResponseDto.CreatedResponse;
import alekssandher.barber_shop_api.dto.response.ApiResponseDto.GetResponse;
import alekssandher.barber_shop_api.dto.response.ErrorResponses.InternalErrorCustom;
import alekssandher.barber_shop_api.dto.schedule.AppointMentResponseDto;
import alekssandher.barber_shop_api.dto.schedule.ScheduleRequestDto;
import alekssandher.barber_shop_api.entity.ScheduleEntity;
import alekssandher.barber_shop_api.exceptions.Exceptions.BadRequestException;
import alekssandher.barber_shop_api.exceptions.Exceptions.ConflictException;
import alekssandher.barber_shop_api.exceptions.Exceptions.NotFoundException;
import alekssandher.barber_shop_api.mappers.ScheduleMapper;
import alekssandher.barber_shop_api.service.IScheduleService;
import alekssandher.barber_shop_api.service.query.IScheduleQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("schedule")
public class ScheduleController {
    private final IScheduleService service;
    private final IScheduleQueryService queryService;

    public ScheduleController(IScheduleService service, IScheduleQueryService queryService) {
        this.service = service;
        this.queryService = queryService;
    }

    @Operation(summary = "Create a new schedule", description = "Creates a new schedule entry.")
    @ApiResponse(responseCode = "201", description = "Schedule created successfully",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreatedResponse.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request: Invalid data provided",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)))
    @ApiResponse(responseCode = "409", description = "Conflict: Schedule entry already exists",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictException.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class)))
    @PostMapping
    public ResponseEntity<CreatedResponse> create(@RequestBody @Valid ScheduleRequestDto dto, HttpServletRequest request) throws ConflictException {
        var entity = ScheduleMapper.toEntity(dto);
        service.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreatedResponse(request));
    }

    @Operation(summary = "Delete a schedule", description = "Deletes a schedule entry by its ID.")
    @ApiResponse(responseCode = "204", description = "Schedule deleted successfully")
    @ApiResponse(responseCode = "400", description = "Bad Request: Invalid ID format",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)))
    @ApiResponse(responseCode = "404", description = "Schedule not found",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class)))
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull final long id) throws NotFoundException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List schedules by month", description = "Retrieves all schedules for a given month and year.")
    @ApiResponse(responseCode = "200", description = "Schedules retrieved successfully",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponse.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request: Invalid year or month format",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)))
    @ApiResponse(responseCode = "404", description = "No schedules found for the given month",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class)))
    @GetMapping("{year}/{month}")
    public ResponseEntity<GetResponse<AppointMentResponseDto>> listMonth(
        @PathVariable @Min(1900) @Max(2100) final int year, 
        @PathVariable @Min(1) @Max(12) final int month, 
        HttpServletRequest request
    ) throws NotFoundException 
    {
        var yearMonth = YearMonth.of(year, month);
        var startAt = yearMonth.atDay(1).atTime(0, 0, 0, 0).atOffset(UTC);
        var endAt = yearMonth.atEndOfMonth().atTime(23, 59, 59, 999_999_999).atOffset(UTC);

        List<ScheduleEntity> entities = queryService.findInMonth(startAt, endAt);
        
        var response = ScheduleMapper.toAppointMentResponseDto(year, month, entities);

        return ResponseEntity.status(HttpStatus.OK).body(new GetResponse<AppointMentResponseDto>(response, request));
    }
}

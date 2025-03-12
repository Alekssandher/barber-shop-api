package alekssandher.barber_shop_api.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alekssandher.barber_shop_api.dto.client.ClientRequestDto;
import alekssandher.barber_shop_api.dto.client.ClientResponseDto;
import alekssandher.barber_shop_api.dto.response.ApiResponseDto.CreatedResponse;
import alekssandher.barber_shop_api.dto.response.ApiResponseDto.DeleteResponse;
import alekssandher.barber_shop_api.dto.response.ApiResponseDto.GetResponse;
import alekssandher.barber_shop_api.dto.response.ErrorResponses.InternalErrorCustom;
import alekssandher.barber_shop_api.entity.ClientEntity;
import alekssandher.barber_shop_api.exceptions.Exceptions.ConflictException;
import alekssandher.barber_shop_api.exceptions.Exceptions.NotFoundException;
import alekssandher.barber_shop_api.mappers.ClientMapper;
import alekssandher.barber_shop_api.service.IClientService;
import alekssandher.barber_shop_api.service.query.IClientQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("clients")
public class ClientController {

    private final IClientService service;
    private final IClientQueryService queryService;

    public ClientController(IClientService service, IClientQueryService queryService) {
        this.service = service;
        this.queryService = queryService;
    }

    // Documentation
    @Operation(summary = "Create a new client", description = "Creates a new client in the system.")
    @ApiResponse(responseCode = "201", description = "Client created successfully",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreatedResponse.class)))
    @ApiResponse(responseCode = "409", description = "Conflict: Client already exists",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictException.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class)))

    @PostMapping
    public ResponseEntity<CreatedResponse> save(@RequestBody @Valid ClientRequestDto dto, HttpServletRequest request) throws ConflictException {
        var entity = ClientMapper.toEntity(dto);
        service.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreatedResponse(request));
    }

    // Documentation
    @Operation(summary = "Update an existing client", description = "Updates a client based on the provided ID.")
    @ApiResponse(responseCode = "201", description = "Client updated successfully",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreatedResponse.class)))
    @ApiResponse(responseCode = "404", description = "Client not found",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    @ApiResponse(responseCode = "409", description = "Conflict: Update could not be processed",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictException.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class)))

    @PostMapping("{id}")
    public ResponseEntity<CreatedResponse> update(@PathVariable final long id, @RequestBody @Valid final ClientRequestDto dto, HttpServletRequest request) throws ConflictException, NotFoundException {
        var entity = ClientMapper.toEntity(dto);
        service.update(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreatedResponse(request));
    }

    // Documentation
    @Operation(summary = "Delete a client", description = "Deletes a client by its ID.")
    @ApiResponse(responseCode = "204", description = "Client deleted successfully") 
    @ApiResponse(responseCode = "404", description = "Client not found",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class)))

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id) throws NotFoundException {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    // Documentation
    @Operation(summary = "List all clients", description = "Retrieves a list of all registered clients.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class)))

    @GetMapping
    public ResponseEntity<GetResponse<List<ClientResponseDto>>> list(HttpServletRequest request) {
        List<ClientEntity> entities = queryService.list();

        var entitiesConverted = entities.stream().map(ClientMapper::toResponseDto).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new GetResponse<>(entitiesConverted, request));
    }

    @Operation(summary = "Find client by ID", description = "Retrieves a client by ID.")
    @ApiResponse(responseCode = "200", description = "Client retrivied successfully", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponse.class)))
    @ApiResponse(responseCode = "404", description = "Client not found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class)))
    @GetMapping("{id}")
    public ResponseEntity<GetResponse<ClientResponseDto>> find(@PathVariable long id, HttpServletRequest request) throws NotFoundException
    {
        ClientEntity entity = queryService.findById(id);
        ClientResponseDto responseDto = ClientMapper.toResponseDto(entity);
        return ResponseEntity.status(HttpStatus.OK).body(new GetResponse<ClientResponseDto>(responseDto, request));
    }
}

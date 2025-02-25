package com.svsa.ct.api.controller;

import com.svsa.ct.api.dtos.DenunciaModel;
import com.svsa.ct.api.exceptionsHandler.ExceptionMessage;
import com.svsa.ct.domain.model.Denuncia;
import com.svsa.ct.domain.model.Usuario;
import com.svsa.ct.domain.model.enums.AgenteViolador;
import com.svsa.ct.domain.model.enums.DireitoViolado;
import com.svsa.ct.domain.model.enums.OrigemDenuncia;
import com.svsa.ct.domain.service.DenunciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.ArraySchema;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Denuncias")
@RestController
@RequestMapping("/api/restricted/denuncia")
public class DenunciaController {
    @Autowired //inject
    private DenunciaService denunciaService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Criar nova denuncia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A denuncia foi criada com sucesso", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Denuncia.class))}),
            @ApiResponse(responseCode = "400", description = "Não foi possível adionar uma nova denuncia por conta de parametros inválidos", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))}),
            @ApiResponse(responseCode = "403", description = "O usuário não está autenticado", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))})
    })
    @PostMapping
    public ResponseEntity<DenunciaModel> saveDenuncia(@RequestBody @Valid DenunciaModel denunciaDto) {

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Denuncia denuncia = modelMapper.map(denunciaDto, Denuncia.class);
        denuncia.setConselheiro(usuario);
        denuncia = denunciaService.saveDenuncia(denuncia);

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(denuncia, DenunciaModel.class));
    }

    @Operation(summary = "Retornar uma denuncia por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = DenunciaModel.class))}),
            @ApiResponse(responseCode = "400", description = "Não foi possível retornar denuncia pois o parametro 'id' da requisição é inválido", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))}),
            @ApiResponse(responseCode = "403", description = "O usuário não está autenticado", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))}),
            @ApiResponse(responseCode = "404", description = "Denuncia não encontrada", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<DenunciaModel> getDenunciaById(@PathVariable Long id) {
        Denuncia denuncia = denunciaService.buscarDenuncia(id);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(denuncia, DenunciaModel.class));
    }

    @Operation(summary = "Alterar denuncia associada a um id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = DenunciaModel.class))}),
            @ApiResponse(responseCode = "400", description = "Não foi possível atualizar a  denuncia por conta de parametros inválidos", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))}),
            @ApiResponse(responseCode = "403", description = "O usuário não está autenticado", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))}),
            @ApiResponse(responseCode = "404", description = "Denuncia não encontrada", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))})
    })
    @PutMapping("/{id}")
    public ResponseEntity<DenunciaModel> atualizarDenuncia(@Parameter(description = "Representação uma denuncia", required = true)
                                                              @RequestBody @Valid DenunciaModel denunciaDto,
                                                              @Parameter(description = "ID de uma denuncia existente", example = "1", required = true)
                                                              @PathVariable Long id) {

        Denuncia denuncia = denunciaService.buscarDenuncia(id);
        BeanUtils.copyProperties(denunciaDto, denuncia, "id");
        denuncia = denunciaService.saveDenuncia(denuncia);

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(denuncia, DenunciaModel.class));

    }


    @Operation(summary = "Retorna uma lista de todas as denuncias cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de denúncias",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = DenunciaModel.class)))),
            @ApiResponse(responseCode = "403", description = "O usuário não está autenticado", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))})
               })
    @GetMapping
    public ResponseEntity<List<DenunciaModel>> getDenuncias() {
        List<Denuncia> denuncias = denunciaService.buscarDenuncias();
        denuncias.forEach(d -> d.getConselheiro().setSenha(null));
        return ResponseEntity.status(HttpStatus.OK).body(
                denuncias.stream()
                .map(denuncia -> modelMapper.map(denuncia, DenunciaModel.class))
                .collect(Collectors.toList())
        );
    }

    @Operation(summary = "Apagar denuncia por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Não retorna nenhum conteúdo, a denuncia foi apagaa com sucesso"),
            @ApiResponse(responseCode = "400", description = "Não foi possível retornar denuncia pois o parametro 'id' está no formato inválido ", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))}),
            @ApiResponse(responseCode = "403", description = "O usuário não está autenticado", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))}),
            @ApiResponse(responseCode = "404", description = "Denuncia não encontrada", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))})
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDenuncia(@Parameter(description = "ID de uma denuncia existente", example = "1", required = true) @PathVariable Long id) {
        denunciaService.apagarDenuncia(id);
    }

    @Operation(summary = "Retorna todos os valores da enumeração de Origem de Denuncia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrigemDenuncia.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))}),
    })
    @GetMapping("/origem-denuncia")
    public OrigemDenuncia[] getAllorigensDenuncia() {
        return OrigemDenuncia.values();
    }

    @Operation(summary = "Retorna todos os valores do enumeração de Direitos Violados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = DireitoViolado.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))}),
    })
    @GetMapping("/direito-violado")
    public DireitoViolado[] getAlldireitoViolado() {
        return DireitoViolado.values();
    }

    @Operation(summary = "Retorna todos os valores do enumeração de Agentes violadores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = AgenteViolador.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))}),
    })
    @GetMapping("/agente-violador")
    public AgenteViolador[] getAllAgenteViolador() {
        return AgenteViolador.values();
    }

}

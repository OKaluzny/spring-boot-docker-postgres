package com.kaluzny.demo.api;

import com.kaluzny.demo.domain.Automobile;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Api(value = "TruckResource", tags = "microservice-assistant")
@SwaggerDefinition(tags = @Tag(name = "microservice-assistant", description = "Resource interface for microservice-assistant"))
public interface AutomobileResource {

    @ApiOperation(value = "Endpoint to create an automobile", response = Automobile.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 201, message = " ")})
    Automobile saveAutomobile(
            @ApiParam("Automobile")
            @NotNull
            @RequestBody Automobile automobile);

    @ApiOperation(value = "Endpoint to get Automobile by id", response = Automobile.class)
    @ApiResponse(code = 200, message = "Success")
    Collection<Automobile> getAllAutomobiles();

    Automobile getAutomobileById(@PathVariable Long id);

    Collection<Automobile> findAutomobileByName(@RequestParam(value = "name") String name);

    Automobile refreshAutomobile(long id, @RequestBody Automobile automobile);

    void removeAutomobileById(@PathVariable Long id);

    void removeAllAutomobiles();

}

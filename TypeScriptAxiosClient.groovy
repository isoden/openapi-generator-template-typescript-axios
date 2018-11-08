@Grab('io.swagger:swagger-codegen-cli:2.3.1')
import io.swagger.codegen.*;
import io.swagger.codegen.languages.*;

class TypeScriptAxiosClientCodegen extends TypeScriptJqueryClientCodegen {
  String name = "typescript-axios"
  String help = "Custom Typescript code generator"

  TypeScriptAxiosClientCodegen() {
    super()
    this.modelPropertyNaming = "original"
  }

  @Override
  public Map<String, Object> postProcessModels(Map<String, Object> objs) {
    // process enum in models
    List<Object> models = (List<Object>) postProcessModelsEnum(objs).get("models");

    for (Object _mo : models) {
      Map<String, Object> mo = (Map<String, Object>) _mo;
      CodegenModel cm = (CodegenModel) mo.get("model");
      cm.imports = new TreeSet(cm.imports);

      // name enum with model name, e.g. StatusEnum => Pet.StatusEnum
      for (CodegenProperty var : cm.vars) {
        if (Boolean.TRUE.equals(var.isEnum)) {
          var.datatypeWithEnum = var.datatypeWithEnum.replace(var.enumName, cm.classname + var.enumName);
        }
      }

      if (cm.parent != null) {
        for (CodegenProperty var : cm.allVars) {
          if (Boolean.TRUE.equals(var.isEnum)) {
            var.datatypeWithEnum = var.datatypeWithEnum.replace(var.enumName, cm.classname + var.enumName);
          }
        }
      }
    }
    return objs;
  }

  public static main(String[] args) {
    SwaggerCodegen.main(args)
  }
}

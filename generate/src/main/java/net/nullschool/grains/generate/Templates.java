package net.nullschool.grains.generate;

/**
 * 2013-02-19<p/>
 *
 * @author Cameron Beccario
 */
final class Templates {

    static Template newGrainInterfaceTemplate(Configuration config) {
        return new TemplateDecl(config, "grain_interface", "grain_interface.stg");
    }

    static Template newBuilderInterfaceTemplate(Configuration config) {
        return new TemplateDecl(config, "builder_interface", "builder_interface.stg");
    }

    static Template newFactoryEnumTemplate(Configuration config) {
        return new TemplateDecl(
            config,
            "factory_enum",
            "factory_enum.stg",
            "grain_impl.stg",
            "builder_impl.stg",
            "serialization_proxy_impl.stg");
    }

    static Template newImportsBlockTemplate(Configuration config) {
        return new TemplateDecl(config, "imports_block", "imports_block.stg");
    }

    private Templates() {
        throw new AssertionError();
    }
}

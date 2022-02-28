package net.digitalingot.featheropt.helpers;

import net.digitalingot.featheropt.mixin.resources.AccessorSimpleResource;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.SimpleResource;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;

public class ResourcesHook {

    public static void closeResource(IResource resource) {
        IOUtils.closeQuietly(resource.getInputStream());
        if (resource instanceof SimpleResource) {
            InputStream inputStream = ((AccessorSimpleResource) resource).featherOpt$getMcmetaInputStream();
            IOUtils.closeQuietly(inputStream);
        }
    }

}

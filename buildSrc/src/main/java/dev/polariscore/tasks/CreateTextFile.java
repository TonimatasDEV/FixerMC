package dev.polariscore.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;
import java.io.FileWriter;
import java.io.IOException;

public abstract class CreateTextFile extends DefaultTask {

    @Inject
    public CreateTextFile() {
        getOutputs().upToDateWhen(task -> false);
        getOutputFile().convention(getProject().getLayout().getBuildDirectory().dir("generated").map(dir -> dir.file(getFileName().get())));
    }

    @Input
    public abstract ListProperty<String> getContent();

    @Input
    public abstract Property<String> getFileName();

    @OutputFile
    public abstract RegularFileProperty getOutputFile();

    @TaskAction
    public void generateFile() {
        String content = String.join("\n", getContent().get());

        try {
            FileWriter writer = new FileWriter(getOutputFile().get().getAsFile());
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing the file: ", e);
        }
    }
}

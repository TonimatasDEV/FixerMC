package dev.polariscore.tasks;

import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.ArtifactRepository;
import org.gradle.api.artifacts.repositories.UrlArtifactRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Utils {
    public static List<String> getDependencies(Configuration configuration) {
        List<String> libraries = new ArrayList<>();
        Set<ResolvedArtifact> artifacts = configuration.getResolvedConfiguration().getResolvedArtifacts();

        for (ResolvedArtifact artifact : artifacts) {
            String library = artifact.getModuleVersion().getId().getGroup() + ":" +
                    artifact.getModuleVersion().getId().getName() + ":" +
                    artifact.getModuleVersion().getId().getVersion();
            libraries.add(library);
        }

        return libraries;
    }

    public static List<String> getRepositories(RepositoryHandler handler) {
        List<String> repositories = new ArrayList<>();

        for (ArtifactRepository repository : handler) {
            if (repository instanceof UrlArtifactRepository urlArtifactRepository) {
                repositories.add(urlArtifactRepository.getUrl().toString());
            }
        }

        return repositories;
    }
}


package de.ens;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.transport.FilterSpec;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        CloneCommand command = Git.cloneRepository();
        command.setDirectory(new File("/Users/nikita.efremov/IdeaProjects/jgit_partial_clone/test_dir_git"));
        command.setURI("https://github.com/spring-io/initializr.git");
        // while using "5.1" jgit version
//        command.setTransportConfigCallback((transport -> transport.setFilterBlobLimit(0)));
        // while using "5.13.1" jgit version
        command.setTransportConfigCallback(transport -> {
            try {
                transport.setFilterSpec(FilterSpec.fromFilterLine("blob:none"));
//                transport.setFilterSpec(FilterSpec.fromFilterLine("tree:0"));
            } catch (PackProtocolException ex) {
                throw new RuntimeException(ex);
            }
        });
//        command.setNoCheckout(true);

        try {
            command.call();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}

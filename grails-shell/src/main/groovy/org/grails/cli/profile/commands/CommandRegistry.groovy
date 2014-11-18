package org.grails.cli.profile.commands

import groovy.transform.CompileStatic
import org.grails.cli.profile.Command
import org.grails.cli.profile.ProfileRepository
import org.grails.cli.profile.ProfileRepositoryAware
import org.grails.cli.profile.steps.StepFactory

/*
 * Copyright 2014 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Registry of available commands
 *
 * @author Graeme Rocher
 * @since 3.0
 */
@CompileStatic
class CommandRegistry {

    private static Map<String, Command> registeredCommands = [:]

    static {
        def commands = ServiceLoader.load(Command).iterator()

        while(commands.hasNext()) {
            Command command = commands.next()
            registeredCommands[command.name] = command
        }
    }

    /**
     * Returns a command for the given name and repository
     *
     * @param name The command name
     * @param repository The {@link ProfileRepository} instance
     * @return A command or null of non exists
     */
    static Command getCommand(String name, ProfileRepository repository) {
        def command = registeredCommands[name]
        if(command instanceof ProfileRepositoryAware) {
            command.profileRepository = repository
        }
        return command
    }
}
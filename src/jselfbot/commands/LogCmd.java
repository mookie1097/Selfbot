/**
 * I think I need to include this License 
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
package jselfbot.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jselfbot.Command;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Command to log the last X messages in chat
 * 
 * @author Vi
 *
 */
public class LogCmd extends Command {

	public LogCmd() {
		this.name = "log";
		this.description = "logs the conversation";
		this.arguments = "<Number of messages>";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jselfbot.Command#execute(java.lang.String,
	 * net.dv8tion.jda.core.events.message.MessageReceivedEvent)
	 */
	@Override
	protected void execute(String args, MessageReceivedEvent event) {
		int num = 0;
		final int test = 100;
		try {
			if (!args.isEmpty())
				num = Integer.parseInt(args);
		} catch (NumberFormatException e) {
			tempReply("`" + args + "` is not a valid number", event);
			return;
		}
		MessageHistory history = event.getChannel().getHistory();
		 List<Message> list = new ArrayList<Message>() ;

		try {
			while (num > 0) {
				System.out.println("-------- " + num + " --------");
				list.addAll(history.retrievePast(num % test).complete());
				


					System.out.println("History:");
					//history.getRetrievedHistory()
					//		.forEach(m -> System.out.println(m.getAuthor().getName() + ": " + m.getContentRaw()));

					System.out.println("Printing " + list.size() + " messages.");

					// tempReply("Printing " + listt.size() + " messages.", event);

					list.forEach(m ->{ 						
						String time = m.getCreationTime().toLocalDateTime().toString();
						String user = m.getAuthor().getName();
						System.out.println(time + " | " + user + ": " + m.getContentDisplay());
						});
					
					//list.get(1).getEditedTime();
					System.out.println("Done printing this section");


				
				num -= test;
			}

			System.out.println("-------- " + "done " + " --------");
			System.out.println("-------- " + history.getRetrievedHistory().size() + " --------");
			int i = 0;
			for (Message x : history.getRetrievedHistory())
				System.out.println(i++);
			history.getRetrievedHistory()
					.forEach(m -> System.out.println(m.getAuthor().getName() + ": " + m.getContentRaw()));

		} catch (Exception e) {
			System.out.println("|Exception occured:");
			// System.out.println("|" + e.toString() );
			e.printStackTrace();
			System.out.println("|Could not retrieve messages.");
			// tempReply("Could not retrieve messages.", event);
		}
		System.out.println("done with log");

	}

}

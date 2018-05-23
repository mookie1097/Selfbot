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

import java.util.List;
import java.util.stream.Collectors;

import jselfbot.Command;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Command to log the last X messages in chat
 * 
 * @author Vi
 *
 */
public class LogFromCmd extends Command {


	public LogFromCmd() {
    this.name = "log";
    this.description = "logs the conversation";
    this.arguments = "<id of start message>";
	}

	/* (non-Javadoc)
	 * @see jselfbot.Command#execute(java.lang.String, net.dv8tion.jda.core.events.message.MessageReceivedEvent)
	 */
	@Override
	protected void execute(String args, MessageReceivedEvent event) {
		int num = 0;
		try {
      if(!args.isEmpty())
          num = Integer.parseInt(args);
      } catch(NumberFormatException e) {
          tempReply("`"+args+"` is not a valid number", event);
          return;
      }
		
		try {
      event.getChannel().getHistory().retrievePast(num==100 ? 100 : num+1).queue(success -> {
          List<Message> list = success.stream().collect(Collectors.toList());
          		//success.stream().filter(m -> event.getJDA().getSelfUser().equals(m.getAuthor()) &&
          	  //   !event.getMessage().equals(m)).collect(Collectors.toList());
          //list.forEach(m -> m.delete().queue());
          
          tempReply("Printing "+list.size()+" messages.", event);
          
          list.forEach(m -> System.out.println(m.getAuthor().getName() + ": " + m.getContentRaw()));
          
      }, failure -> {
          tempReply("Failed retrieve messages.", event);
      });
  } catch(Exception e) {
      tempReply("Could not retrieve messages.", event);
  }

	}

}

By default JBoss 7 doesn't start with messaging.
Therefore, to start JBoss you must explicitly use a different configuration that enables messaging:

standalone.bat --server-config=standalone-full.xml

To create a queue in JBoss 7 you would have to make sure you give “java:jboss/exported/” before giving the 
JNDI name of the queue. 

In the standalone/configuration/standalone-full.xml file need to give permission for the Application User role send 
and consume messages. If the role is dev, then:

Change this:

                <security-settings>
                    <security-setting match="#">
                        <permission type="send" roles="guest"/>
                        <permission type="consume" roles="guest"/>
                        <permission type="createNonDurableQueue" roles="guest"/>
                        <permission type="deleteNonDurableQueue" roles="guest"/>
                    </security-setting>
                </security-settings>

To this:
                <security-settings>
                    <security-setting match="#">
                        <permission type="send" roles="guest dev"/>
                        <permission type="consume" roles="guest dev"/>
                        <permission type="createNonDurableQueue" roles="guest"/>
                        <permission type="deleteNonDurableQueue" roles="guest"/>
                    </security-setting>
                </security-settings>
                
If you prefer JBoss Web Interface:  Administration Console > Profile > Messaging Provider > View.
On the Security tab, just click Add  and configure the role.
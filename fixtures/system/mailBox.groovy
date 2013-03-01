import de.dewarim.goblin.social.MailBoxType

fixture{
    inBox(MailBoxType, name:'mail.inbox')
    outBox(MailBoxType, name:'mail.outbox')
    archive(MailBoxType, name:'mail.archive')    
}
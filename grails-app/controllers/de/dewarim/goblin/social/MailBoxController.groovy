package de.dewarim.goblin.social

import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.BaseController
import de.dewarim.goblin.pc.PlayerCharacter

/**
 *
 */
class MailBoxController extends BaseController{

    /**
     * Show mailbox area
     */
    @Secured(['ROLE_USER'])
    def index() {
        def pc = fetchPc()

        def box = fetchMailBox(pc)
        def mails = fetchMails(pc, box)
        log.debug("mailBox::$box")
        return [
                mailBox:box,
                pc:pc,
                mails:mails,
                max: params.max?.toInteger() ?: 10,
                offset:params.offset?.toInteger() ?: 0
        ]
    }

    /**
     * Write a new mail
     */
    @Secured(['ROLE_USER'])
    def writeMail() {
        def pc = fetchPc()

        return [
                pc:pc,
        ]
    }


    /**
     * Send a new mail
    */
    @Secured(['ROLE_USER'])
    def sendMail() {
        def pc = fetchPc()

        PlayerCharacter recipient = PlayerCharacter.findByName(params.recipientName)
        params.subject = params.subject?.encodeAsHTML()
        params.contet = params.content?.encodeAsHTML()

        params.sender = pc
        params.recipient = recipient
        params.box = pc.fetchOutBox()

        Mail mail = new Mail(params)

        params.box = recipient?.fetchInbox()
        Mail forRecipient = new Mail(params)

        if(! mail.validate() || ! forRecipient.validate()){
            render(view:'writeMail', model:[pc:pc, mail:mail])
            return
        }
        else{
            mail.save()
            forRecipient.save()
            flash.message = message(code:'mail.sent.success')
            redirect(action:'index', controller:'mailBox')
            return
        }
    }

    /**
     * Fetch the mailbox specified by params.box (or the inbox).
     * @param pc the current PlayerCharacter
     * @return the requested mailbox or the pc's inbox.
     */
    MailBox fetchMailBox(PlayerCharacter pc){
        MailBox box = null
        if(params.box){
            box = pc.mailBoxes.find{it.id.equals(params.box?.toLong())}
        }
        if(! box){
            box = pc.fetchInbox()
//            if(! box){
//                throw new RuntimeException("Player has no inbox!")
//            }
        }
        return box
    }


    List<Mail> fetchMails(pc,box){
        def max = params.max ?: 10
        def offset = params.offset ?: 0
        if(box?.boxType?.name?.equals('mail.inbox')){
            // the inbox is the only mailbox where all mails inside are _to_ the pc.
            return Mail.findAll("from Mail as m where m.recipient=:recipient and m.box=:box",
                     [recipient:pc, box:box],
                     [max:max.toInteger(), offset:offset.toInteger()])
        }
        else{
             return Mail.findAll("from Mail as m where m.sender=:sender and m.box=:box",
                    [sender:pc, box:box],
                    [max:max.toInteger(), offset:offset.toInteger()])
        }
    }

    /**
    * Show a mailbox
    */
    @Secured(['ROLE_USER'])
    def showBox() {
        log.debug("params:${params}")
        def pc = fetchPc()

        def box = fetchMailBox(pc)
        if(! box){
            render(status:503, text:message(code:'error.mailbx.not.found'))
            return
        }

        def mails = fetchMails(pc, box)
        log.debug("mails:${mails}")
        render(template:'/mailBox/mailbox',
                model:[pc:pc, mailBox:box, mails:mails, max:params.max ?: 10, offset:params.offset ?: 0])
        return
    }

    /**
    * render list of mails
    */
    @Secured(['ROLE_USER'])
    def listMails() {
        def pc = fetchPc()
        def box = fetchMailBox(pc)
        if(! box){
            render(status:503, text:message(code:'error.mailbx.not.found'))
            return
        }

        def mails = fetchMails(pc, box)
        render(template:'/mailBox/list_mails', model:[mailBox:box, mails:mails])
        return
    }

    /**
    * render a single mail
    */
    @Secured(['ROLE_USER'])
    def showMail() {
        def pc = fetchPc()

        Mail mail = Mail.get(params.mail)
        if(mail.recipient.equals(pc)){
            mail.shown = true
            render(template:'/mailBox/mail_message', model:[pc:pc, mail:mail])
        }
        else{
            render(status:503, text:message(code:'error.mail.foreign'))
        }
    }

    /**
     * Send a message via AJAX form.
     */
    @Secured(['ROLE_USER'])
    def sendMessage() {
        def pc = fetchPc()
        def recipient = PlayerCharacter.get(params.recipient)
        if(! recipient){
            render(status:503, text:message(code:'error.mail.recipient.missing'))
            return
        }

        def content = params.replyContent?.encodeAsHTML()
        if(! content){
            render(status:503, text:message(code:'error.mail.no_content'))
            return
        }

        Mail mail = new Mail(subject:params.replySubject?.encodeAsHTML(),
                sender:pc,
                recipient:recipient,
                content:content,
                box:recipient.fetchInbox()
        )
        Mail sentMail = new Mail(subject:params.replySubject?.encodeAsHTML(),
                sender:pc,
                recipient:recipient,
                content:content,
                box:pc.fetchOutBox()
        )
        if(mail.save()){
            sentMail.save()
            render(text:message(code:'mail.sent.success'))
        }
        else{
            render(status:503, text:mail.errors)
        }
    }

    @Secured(['ROLE_USER'])
    def deleteMail() {
        def pc = fetchPc()

        Mail mail = Mail.get(params.mail)
        if(mail.recipient.equals(pc)){
            mail.box.removeFromMails mail
            mail.delete()
            render(text:message(code:'mail.was.deleted'))
        }
        else{
            render(status:503, text:message(code:'error.mail.foreign'))
        }
    }

    @Secured(['ROLE_USER'])
    def archiveMail() {
        def pc = fetchPc()

        Mail mail = Mail.get(params.mail)
        if(mail.recipient.equals(pc)){
            MailBox archiveBox = pc.fetchArchiveBox()
            if(! archiveBox){
                render(status:503, text:message(code:'error.mail.box.missing'))
                return
            }
            mail.box = archiveBox
            archiveBox.addToMails(mail)
            render(text:message(code:'mail.archived'))
        }
        else{
            render(status:503, text:message(code:'error.mail.foreign'))
        }
    }
}

package com.springapp.service;

public class ReferralEmailingService {
    private static final String EMAIL_SUBJECT = "ExamSignal.com : Refer Request";
    private EmailingService emailingService;
    private TwoWayEncryptionService twoWayEncryptionService;

    public void setTwoWayEncryptionService(TwoWayEncryptionService twoWayEncryptionService) {
        this.twoWayEncryptionService = twoWayEncryptionService;
    }

    public void setEmailingService(EmailingService emailingService) {
        this.emailingService = emailingService;
    }

    public boolean sendReferralEmail(String senderUserName,String senderUniqueId, String recipientEmailId){
        try{
        String encryptedSenderUniqueId = twoWayEncryptionService.encrypt(senderUniqueId);
        encryptedSenderUniqueId = encryptedSenderUniqueId.replace("+","%2B");
        String encryptedReceiverEmailId = twoWayEncryptionService.encrypt(recipientEmailId);
        encryptedReceiverEmailId = encryptedReceiverEmailId.replace("+","%2B");
        String emailContent = "Hi,<br />"
                + senderUserName
                +" wants you to try <a href='www.examsignal.com'>ExamSignal </a> ! Click the below link to register. " +
                "<br /><b><a href='http://www.examsignal.com/referralListener?e="+encryptedSenderUniqueId+"&r="+encryptedReceiverEmailId+"'>Register</a></b>" +
                " <br /><br /> Thanks, <br />- The ExamSignal.com Team";

            return emailingService.email(recipientEmailId,emailContent,EMAIL_SUBJECT);
        } catch (Exception e){
            return false;
        }

    }
}

package com.my.repairagency007.model.entity;

public enum PaymentStatus {


        PANDING_PAYMENT, PAID, CANCELED;

        public static PaymentStatus getPaymentStatusId(Request request) {
            int paymentStatusId = request.getPaymentStatusId();
            return PaymentStatus.values()[--paymentStatusId];
        }

        public String getName() {
            return name().toLowerCase();
        }
}

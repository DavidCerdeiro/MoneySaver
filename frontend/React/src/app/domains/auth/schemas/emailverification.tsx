import { z } from "zod";
 
//Schema for Email Verification form validation
export const createEmailVerificationSchema  = (t: (key: string) => string) => z.object({
  code: z.string().min(6, { message: t('otp.length') }).regex(/^\d{6}$/, { message: t('otp.otpContent') }),
  email: z.string().email().optional().or(z.literal("")),
  locale: z.string().optional(),
});

export type EmailVerificationData = z.infer<ReturnType<typeof createEmailVerificationSchema>>;
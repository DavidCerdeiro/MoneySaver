import { z } from 'zod';

// Schema for Reset Password form validation
export const createResetPasswordSchema  = (t: (key: string) => string) => z.object({
  email: z.string().email().optional(),
  locale: z.string().optional(),
  newPassword: z.string().min(8, { message: t('errors.passwordLength') })
    .regex(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).+$/, {
      message: t('errors.passwordChars') ,
      }),
    confirmPassword: z.string().min(8, { message: t('errors.passwordLength') }),
  }).refine((data) => data.newPassword === data.confirmPassword, {
    message: t('errors.passwordMismatch'),
    path: ["confirmPassword"],
});
export type ResetPasswordData = z.infer<ReturnType<typeof createResetPasswordSchema>>;
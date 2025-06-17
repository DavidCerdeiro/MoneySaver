import { z } from "zod";

//Schema for Forgot Password form validation
export const createForgotPasswordSchema  = (t: (key: string) => string) => z.object({
  email: z.string().email({ message: t('errors.requiredField') }),
  locale: z.string().optional(),
});

export type ForgotPasswordData = z.infer<ReturnType<typeof createForgotPasswordSchema>>;
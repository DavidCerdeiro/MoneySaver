import { z } from "zod";
 
export const createForgotPasswordSchema  = (t: (key: string) => string) => z.object({
  email: z.string().email({ message: t('errors.requiredField') }),
});

export type ForgotPasswordData = z.infer<ReturnType<typeof createForgotPasswordSchema>>;
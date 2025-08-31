import { z } from "zod";

export const createAccountCheckSchema  = () => z.object({
    reportId: z.string().min(1, { message: 'errors.requiredField' }),
})

export type AccountCheckData = z.infer<ReturnType<typeof createAccountCheckSchema>>;
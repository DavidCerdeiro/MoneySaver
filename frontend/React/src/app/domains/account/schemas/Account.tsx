import { z } from "zod";

export const createAccountSchema  = () => z.object({
    id: z.number().min(1, { message: 'errors.requiredField' }),
    accountCode: z.string().min(2, { message: 'errors.requiredField' }),
    name: z.string().min(2, { message: 'errors.requiredField' }),
    number: z.string().min(2, { message: 'errors.requiredField' }),
    bankName: z.string().min(2, { message: 'errors.requiredField' }),
})

export type AccountData = z.infer<ReturnType<typeof createAccountSchema>>;
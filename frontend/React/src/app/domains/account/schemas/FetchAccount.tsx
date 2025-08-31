import { z } from "zod";

export const createFetchAccountSchema  = () => z.object({
    code: z.string().min(1, { message: 'errors.requiredField' }),
})

export type FetchAccountData = z.infer<ReturnType<typeof createFetchAccountSchema>>;
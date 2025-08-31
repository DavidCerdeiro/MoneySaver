import { z } from "zod";
import { transactionSchema } from "./ExtractTransactionResponse";
import { createSpendingSchema } from "../../spending/schemas/Spending";

export const createAddTransactionSchema = (t: any) => z.object({
  transaction: transactionSchema,
  spending: createSpendingSchema(t),
});

export type AddTransactionData = z.infer<ReturnType<typeof createAddTransactionSchema>>;

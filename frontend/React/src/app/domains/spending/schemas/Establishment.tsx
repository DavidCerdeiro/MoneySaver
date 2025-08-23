import { z } from "zod";

export const createEstablishmentDataSchema = z.object({
  id: z.number().optional(),
  name: z.string().optional(),
});

export type EstablishmentData = z.infer<typeof createEstablishmentDataSchema>;

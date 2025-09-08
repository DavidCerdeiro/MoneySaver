import { useTranslation } from "react-i18next";
import type { CategoryData } from "../../category/schemas/Category";
import { Label } from "../../shared/components/label";
import { Input } from "../../shared/components/input";
import { CategoryCombobox } from "../../category/components/CategoryCombobox";
import { useEffect, useState } from "react";
import { createGoalSchema, type GoalData } from "../schemas/Goal";
import { zodResolver } from "@hookform/resolvers/zod/dist/zod.js";
import { useForm } from "react-hook-form";
import { Button } from "../../shared/components/button";
import { addGoal } from "../application/GoalService";
import { toast } from "sonner";

type AddGoalFormProps = {
    categories: CategoryData[];
    refreshGoals: () => void;
};

export function AddGoalForm({ categories, refreshGoals }: AddGoalFormProps) {
    const { t } = useTranslation();
    const schema = createGoalSchema(t);
    const [selectedCategory, setSelectedCategory] = useState<CategoryData | null>(null);

    const {
        register,
        handleSubmit,
        reset,
        setValue,
        formState: { errors, isSubmitting },
    } = useForm<GoalData>({
        resolver: zodResolver(schema)
    });

    useEffect(() => {
        if (selectedCategory) {
            setValue("idCategory", selectedCategory?.id || 0);
        } else {
            setValue("idCategory", 0);
        }
    }, [selectedCategory, setValue]);

    const onSubmitForm = async (data: GoalData) => {
        await addGoal(data);

        toast.success(t("domains.goal.add.success"));
        reset();
        setSelectedCategory(null);
        refreshGoals();
    };

    return (
        <div className="form-container mt-5">
            <form onSubmit={handleSubmit(onSubmitForm)}>
                <div className="flex flex-col md:flex-row gap-4 mt-4">
                    <div className="w-full md:w-4/12">
                        <Label htmlFor="name" className="label">{t("domains.goal.name")}</Label>
                        <Input id="name" className="input-form" {...register('name')} />
                        {errors.name && <p className="text-red-500 text-sm">{errors.name.message}</p>}
                    </div>
                    <div className="w-full md:w-4/12">
                        <Label htmlFor="targetAmount" className="label">{t("domains.goal.targetAmount")}</Label>
                        <Input id="targetAmount" type="number" className="input-form" {...register('targetAmount', { valueAsNumber: true })} />
                        {errors.targetAmount && <p className="text-red-500 text-sm">{errors.targetAmount.message}</p>}
                    </div>
                    <div className="w-full md:w-5/12">
                        <Label htmlFor="category" className="label">{t("domains.goal.category")}</Label>
                        <CategoryCombobox
                            categories={categories}
                            selectedCategory={selectedCategory}
                            setSelectedCategory={setSelectedCategory}
                            disabled={false}
                            setSelectedEmoji={() => {}} setSelectedIdEmoji={() => {}} setEmojiIsNative={() => {}} 
                    />
                    {errors.idCategory && <p className="text-red-500 text-sm">{errors.idCategory.message}</p>}
                    </div>
                </div>
                <div className="flex justify-center mt-5">
                    <Button type="submit" disabled={isSubmitting} className="button-green">
                        {t("domains.goal.add.submit")}
                    </Button>
                </div>
            </form>
        </div>
    );
}
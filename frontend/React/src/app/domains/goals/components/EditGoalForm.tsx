import { useEffect, useState } from "react";
import type { CategoryData } from "../../category/schemas/Category";
import { createGoalSchema, type GoalData } from "../schemas/Goal";
import { GoalCombobox } from "./GoalCombobox";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod/dist/zod.js";
import { useTranslation } from "react-i18next";
import { CategoryCombobox } from "../../category/components/CategoryCombobox";
import { Input } from "../../shared/components/input";
import { Label } from "../../shared/components/label";
import { Dialog, DialogTrigger, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogClose, DialogTitle } from "@/app/domains/shared/components/dialog";
import { Button } from "../../shared/components/button";
import { deleteGoal, editGoal } from "../application/GoalService";
import { toast } from "sonner";

type EditGoalProps = {
  goals: GoalData[];
  categories: CategoryData[];
  refreshGoals: () => void;
};

export function EditGoalForm({ goals, categories, refreshGoals }: EditGoalProps) {
    const { t } = useTranslation();
    const [selectedGoal, setSelectedGoal] = useState<GoalData | null>(null);
    const [selectedCategory, setSelectedCategory] = useState<CategoryData | null>(null);
    const schema = createGoalSchema(t);

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
    
    useEffect(() => {
        if (selectedGoal) {
            setValue("id", selectedGoal?.id || 0);
            setValue("idCategory", selectedGoal?.idCategory || 0);
            setSelectedCategory(categories.find((cat) => cat.id === selectedGoal?.idCategory) || null);
            setValue("name", selectedGoal?.name || "");
            setValue("targetAmount", selectedGoal?.targetAmount || 0);
        } else {
            setValue("idCategory", 0);
        }
    }, [selectedGoal, setValue]);

    const onSubmit = async (data: GoalData) => {
        await editGoal(data);

        toast.success(t("domains.goal.edit.success"));
        reset();
        setSelectedGoal(null);
        setSelectedCategory(null);
        await refreshGoals();
    };

    const handleDelete = async () => {
       if (!selectedGoal) return;
       await deleteGoal(selectedGoal?.id || 0);

       toast.success(t("domains.goal.delete.success"));
       reset();
       setSelectedGoal(null);
       setSelectedCategory(null);
       await refreshGoals();
    };

    return (
        <>
            
            <div className='form-container'>
                <form>
                    <div className="flex justify-center">
                        <div className="w-full md:w-1/2">
                            <GoalCombobox
                                goals={goals}
                                selectedGoal={selectedGoal}
                                setSelectedGoal={setSelectedGoal}
                            />
                        </div>
                    </div>
                    <div className="flex flex-col md:flex-row gap-4 mt-4">
                        <div className="w-full md:w-4/12">
                        <Label htmlFor="name" className="label">{t("domains.goal.name")}</Label>
                        <Input id="name" className="mobile-form-control" {...register('name')} />
                        </div>
                        <div className="w-full md:w-4/12">
                            <Label htmlFor="targetAmount" className="label">{t("domains.goal.targetAmount")}</Label>
                            <Input id="targetAmount" type="number" className="mobile-form-control" {...register('targetAmount', { valueAsNumber: true })} />
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
                    <div className="flex flex-col md:flex-row justify-center items-center gap-4 mt-5">
                        <Dialog>
                            <DialogTrigger asChild disabled={!selectedCategory || isSubmitting}>
                                <Button className="button-neutral flex justify-center mt-5 gap-4">{t('domains.goal.edit.submit')}</Button>
                            </DialogTrigger>

                            <DialogContent className="dialog-content">
                            <form onSubmit={handleSubmit(onSubmit)}>
                                <DialogHeader className="dialog-header">
                                <DialogTitle className="dialog-title">{t('domains.goal.edit.dialog.title')}</DialogTitle>
                                <DialogDescription className="dialog-description">{t('domains.goal.edit.dialog.description', { name: selectedGoal?.name })}</DialogDescription>
                                </DialogHeader>
                                <DialogFooter className="dialog-footer">
                                <DialogClose asChild>
                                    <Button type="submit" className="button-green"  disabled={isSubmitting}>
                                    {t('domains.goal.edit.dialog.confirm')}
                                    </Button>
                                </DialogClose>
                                <DialogClose asChild>
                                    <Button type="button" variant="secondary" className="button-red">
                                    {t('domains.goal.edit.dialog.cancel')}
                                    </Button>
                                </DialogClose>
                                </DialogFooter>
                            </form>
                            </DialogContent>
                        </Dialog>
                        <Dialog>
                            <DialogTrigger asChild disabled={!selectedCategory || isSubmitting}>
                            <Button className="button-neutral flex justify-center mt-5 gap-4">
                                {t('domains.goal.delete.submit')}
                            </Button>
                            </DialogTrigger>

                            <DialogContent className="dialog-content">
                                <form onSubmit={handleSubmit(handleDelete)}>
                                    <DialogHeader className="dialog-header">
                                        <DialogTitle className="dialog-title">{t('domains.goal.delete.dialog.title')}</DialogTitle>
                                        <DialogDescription className="dialog-description">{t('domains.goal.delete.dialog.description', { name: selectedGoal?.name })}</DialogDescription>
                                    </DialogHeader>
                                    <DialogFooter className="dialog-footer">
                                    <DialogClose asChild>
                                        <Button type="submit" className="button-green"  disabled={isSubmitting}>
                                        {t('domains.goal.delete.dialog.confirm')}
                                        </Button>
                                    </DialogClose>
                                    <DialogClose asChild>
                                        <Button type="button" variant="secondary" className="button-red">
                                        {t('domains.goal.delete.dialog.cancel')}
                                        </Button>
                                    </DialogClose>
                                    </DialogFooter>
                                </form>
                            </DialogContent>
                        </Dialog>
                    </div>
                </form>
            </div>
        </>
    );
}

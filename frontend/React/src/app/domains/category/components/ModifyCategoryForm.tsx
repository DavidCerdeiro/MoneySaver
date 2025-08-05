import { useState } from "react";
import { useForm } from "react-hook-form";

import Picker from '@emoji-mart/react';

import { Button } from '@/app/domains/shared/components/button';
import type { CategoryData } from "../schemas/Category";
import { Label } from "@/app/domains/shared/components/label";
import { Input } from "@/app/domains/shared/components/input";
import { useTranslation } from "react-i18next";
import { getEmojiById } from "./EmojiFunctions";
import { modifyCategory } from "../application/CategoryService";
import { toast } from "sonner";
import { Dialog, DialogTrigger, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogClose, DialogTitle } from "@/app/domains/shared/components/dialog";
import { CategoryCombobox } from "./CategoryCombobox";
type ModifyCategoryFormProps = {
  categories: CategoryData[];
  refreshCategories: () => Promise<void>;
  idUser: number;
};

export function ModifyCategoryForm({ categories, refreshCategories, idUser }: ModifyCategoryFormProps) {
  const { t } = useTranslation();
  const [selectedCategory, setSelectedCategory] = useState<CategoryData | null>(null);
  const [selectedEmoji, setSelectedEmoji] = useState("");
  const [selectedIdEmoji, setSelectedIdEmoji] = useState("");
  const [emojiIsNative, setEmojiIsNative] = useState(false);
  const {
    register,
    handleSubmit,
    setValue,
    reset,
    watch,
    formState: { errors, isSubmitting },
  } = useForm<CategoryData>();
  const watchedName = watch("name");
  const onSubmit = async (formData: CategoryData) => {
    try {
      const requestBody: CategoryData = {
        ...formData,
        icon: selectedIdEmoji,
        idUser: idUser,
        id: selectedCategory?.id,
      };
      console.log("Submitting category modification with data:", requestBody);
      await modifyCategory(requestBody);
      const nativeEmoji = getEmojiById(selectedIdEmoji);
      toast.success(t('domains.category.modify.success', {icon: nativeEmoji, name: requestBody.name}));
      await refreshCategories();
      setSelectedCategory(null);
      setSelectedEmoji("");
      setSelectedIdEmoji("");
      reset();
    } catch (error) {
      console.error('Error modifying category:', error);
      toast.error(t('domains.category.modify.error'));
    }
  };

  return (
    <>
      <CategoryCombobox
        categories={categories}
        selectedCategory={selectedCategory}
        setSelectedCategory={setSelectedCategory}
        setSelectedEmoji={setSelectedEmoji}
        setSelectedIdEmoji={setSelectedIdEmoji}
        setEmojiIsNative={setEmojiIsNative}
        setValue={setValue}
      />

      <div className='flex flex-col items-center justify-center px-4 mt-6'>
        <form className="grid gap-4 md:gap-6">
          <div className="grid gap-2">
            <Label htmlFor="name">{t('domains.category.name')}</Label>
            <Input id="name" {...register("name", { required: t('domains.category.errors.name.required') })} className="input-dark" />
            {errors.name && (
              <p className="text-red-500 text-sm">{errors.name.message}</p>
            )}
          </div>

          <div className="grid gap-2">
            <Label>{t('domains.category.icon')}</Label>
            <div className="flex items-center gap-2">
              <span className="text-2xl">{t('domains.category.add.iconSelected')}{emojiIsNative ? selectedEmoji : getEmojiById(selectedEmoji)}</span>
            </div>
            <Picker
              onEmojiSelect={(emoji: any) => {
                setSelectedEmoji(emoji.native);
                setEmojiIsNative(true);
                setSelectedIdEmoji(emoji.id);
                setValue("icon", emoji.id);
              }}
              previewPosition="none"
              theme="dark"
            />
          </div>

          <input type="hidden" {...register("icon")} />
          

          <Dialog>
            <DialogTrigger asChild disabled={!selectedCategory || isSubmitting}>
              <Button className="button-green">{t('domains.category.add.submit')}</Button>
            </DialogTrigger>

            <DialogContent className="dialog-content">
              <form onSubmit={handleSubmit(onSubmit)}>
                <DialogHeader className="dialog-header">
                  <DialogTitle className="dialog-title">{t('domains.category.modify.dialog.title')}</DialogTitle>
                  <DialogDescription className="dialog-description">{t('domains.category.modify.dialog.description', { icon: getEmojiById(selectedCategory?.icon), name: selectedCategory?.name, newIcon: getEmojiById(selectedIdEmoji), newName: watchedName })}</DialogDescription>
                </DialogHeader>
                <DialogFooter className="dialog-footer">
                  <DialogClose asChild>
                    <Button type="submit" className="button-green"  disabled={isSubmitting}>
                      {t('domains.category.modify.dialog.confirm')}
                    </Button>
                  </DialogClose>
                  <DialogClose asChild>
                    <Button type="button" variant="secondary" className="button-red">
                      {t('domains.category.modify.dialog.cancel')}
                    </Button>
                  </DialogClose>
                </DialogFooter>
              </form>
            </DialogContent>
          </Dialog>

          
        </form>
      </div>
    </>
  );
}

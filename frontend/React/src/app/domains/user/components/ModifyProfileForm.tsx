import { useTranslation } from "react-i18next";
import type { UserData } from "../schemas/User";
import { createUserSchema } from "../schemas/User";
import { zodResolver } from "@hookform/resolvers/zod/dist/zod.js";
import { useForm} from 'react-hook-form';
import { Input } from "../../shared/components/input";
import { Label } from "../../shared/components/label";
import { Button } from "../../shared/components/button";
import { useEffect } from "react";
import { Dialog, DialogTrigger, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogClose, DialogTitle } from "@/app/domains/shared/components/dialog";
import { useNavigate } from "react-router-dom";
import { modifyProfile, verificationEmailToDelete } from "../application/UserService";
import { toast } from "sonner";

type ModifyProfileFormProps = {
  user: UserData | null;
  fetchData: () => Promise<void>;
};

export function ModifyProfileForm({user, fetchData}: ModifyProfileFormProps) {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const schema = createUserSchema(t, true);
    const {
      register,
      handleSubmit,
      reset,
      formState: { errors, isSubmitting },
      } = useForm<UserData>({
        resolver: zodResolver(schema),
        defaultValues: user || {} as UserData,
    });
    // Reset form when user data changes
    useEffect(() => {
      if (user) {
          reset(user);
      }
    }, [user, reset]);

    const handleModify = async (data: UserData) => {
        const { confirmPassword, ...userData } = data;
        
        await modifyProfile(userData);

        await fetchData();
        reset();
        toast.success(t('domains.user.modify.success'));
    };
    const handleDelete = () => {
        // Sending verification email to delete the account
        verificationEmailToDelete({locale: navigator.language});

        navigate("/user/deleteProfile");
    };
    return (
        <>
            <div className="row-input mb-5">
                <div className="w-full">
                    <Label htmlFor="name" className="label">{t('domains.user.name')}</Label>
                    <Input id="name" {...register('name')} className="input-dark" />
                    {errors.name && (
                        <p className="text-red-500 text-sm">{errors.name.message}</p>
                    )}
                </div>
                <div className="w-full">
                    <Label htmlFor="surname" className="label">{t('domains.user.surname')}</Label>
                    <Input id="surname" {...register('surname')} className="input-dark" />
                    {errors.surname && (
                        <p className="text-red-500 text-sm">{errors.surname.message}</p>
                    )}
                </div>
            </div>
            <div className="row-input mb-5">
                <div className="w-full">
                    <Label htmlFor="email" className="label">{t("domains.user.email")}</Label>
                    <Input type="email" id="email" defaultValue={user?.email} {...register('email')} className="input-dark" disabled/>
                    {errors.email && (
                        <p className="text-red-500 text-sm">{errors.email.message}</p>
                    )}
                </div>
            </div>
            <div className="row-input mb-4">
                <div className="w-full">
                    <Label htmlFor="password" className="label">{t("domains.user.password")}</Label>
                    <Input type="password" id="password" {...register('password')} className="input-dark" />
                    {errors.password && (
                        <p className="text-red-500 text-sm">{errors.password.message}</p>
                    )}
                </div>
                <div className="w-full">
                    <Label htmlFor="confirmPassword" className="label">{t("domains.user.confirmPassword")}</Label>
                    <Input type="password" id="confirmPassword" {...register('confirmPassword')} className="input-dark" />
                    {errors.confirmPassword && (
                        <p className="text-red-500 text-sm">{errors.confirmPassword.message}</p>
                    )}
                </div>
            </div>
            <div className="grid gap-2 mb-4">
                <ul className="password-requirements">
                    <li>{t('domains.user.passwordRequirements.length')}</li>
                    <li>{t('domains.user.passwordRequirements.uppercase')}</li>
                    <li>{t('domains.user.passwordRequirements.lowercase')}</li>
                    <li>{t('domains.user.passwordRequirements.number')}</li>
                </ul>
            </div>
            <div className="grid gap-2 mb-4">
            <Dialog>
            <DialogTrigger asChild disabled={isSubmitting}>
              <Button className="button-neutral">{t('domains.user.modify.submit')}</Button>
            </DialogTrigger>

            <DialogContent className="dialog-content">
              <form onSubmit={handleSubmit(handleModify)}>
                <DialogHeader className="dialog-header">
                  <DialogTitle className="dialog-title">{t('domains.user.modify.dialog.title')}</DialogTitle>
                  <DialogDescription className="dialog-description">{t('domains.user.modify.dialog.description')}</DialogDescription>
                </DialogHeader>
                <DialogFooter className="dialog-footer">
                  <DialogClose asChild>
                    <Button type="submit" className="button-green"  disabled={isSubmitting}>
                      {t('domains.user.modify.dialog.confirm')}
                    </Button>
                  </DialogClose>
                  <DialogClose asChild>
                    <Button type="button" variant="secondary" className="button-red">
                      {t('domains.user.modify.dialog.cancel')}
                    </Button>
                  </DialogClose>
                </DialogFooter>
              </form>
            </DialogContent>
          </Dialog>
          <Dialog>
            <DialogTrigger asChild disabled={isSubmitting}>
              <Button className="button-neutral">
                {t('domains.user.delete.title')}
              </Button>
            </DialogTrigger>

            <DialogContent className="dialog-content">
              <form onSubmit={handleSubmit(handleDelete)}>
                <DialogHeader className="dialog-header">
                  <DialogTitle className="dialog-title">{t('domains.user.delete.dialog.title')}</DialogTitle>
                  <DialogDescription className="dialog-description">{t('domains.user.delete.dialog.description')}</DialogDescription>
                </DialogHeader>
                <DialogFooter className="dialog-footer">
                  <DialogClose asChild>
                    <Button type="submit" className="button-green"  disabled={isSubmitting}>
                      {t('domains.user.delete.dialog.confirm')}
                    </Button>
                  </DialogClose>
                  <DialogClose asChild>
                    <Button type="button" variant="secondary" className="button-red">
                      {t('domains.user.delete.dialog.cancel')}
                    </Button>
                  </DialogClose>
                </DialogFooter>
              </form>
            </DialogContent>
          </Dialog>
          </div>
        </>
    );
}

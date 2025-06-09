import React from 'react';
import { Card, CardAction, CardContent, CardFooter, CardHeader, CardTitle, CardDescription } from "@/app/domains/shared/card";
import { Input } from "@/app/domains/shared/input.js";
import { Button } from "@/app/domains/shared/button.js";
import { Label } from "@/app/domains/shared/label.js";
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { Separator } from "@/app/domains/shared/separator";

export function ForgotPasswordForm() {
    const { t } = useTranslation();

    return (
        <div className="min-h-screen flex items-center justify-center px-4">
            <Card className="w-full max-w-sm">
                <CardHeader>
                    <CardTitle className="text-2xl">{t('forgotPassword.title')}</CardTitle>
                    <CardDescription>{t('forgotPassword.description')}</CardDescription>
                </CardHeader>
                <CardContent className="grid gap-4">
                    <form className="grid gap-4 md:gap-6">
                        <div className="grid gap-2">
                            <Label htmlFor="email">{t('user.email')}</Label>
                            <Input id="email" type="email" placeholder={t('forgotPassword.emailPlaceholder')} required/>
                        </div>
                        <Button type="submit" className="w-full">{t('forgotPassword.submit')}</Button>
                    </form>
                </CardContent>
                <Separator className="my-4  bg-gray-200 h-px" />
                <CardFooter className="flex justify-center">
                    <CardAction>
                        <Link to="/login" className="px-0 text-black hover:text-blue-600 transition-colors">{t('forgotPassword.backToLogin')}</Link>
                    </CardAction>
                </CardFooter>
            </Card>
        </div>
    )
}
import React from 'react';
import { Card, CardAction, CardContent, CardFooter, CardHeader, CardTitle, CardDescription } from "@/app/domains/shared/card";
import { Input } from "@/app/domains/shared/input.js";
import { Button } from "@/app/domains/shared/button.js";
import { Label } from "@/app/domains/shared/label.js";
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { Eye, EyeOff } from "lucide-react";


export function SignUpForm() {
    const { t } = useTranslation();

    return (
        <div className="min-h-screen flex items-center justify-center px-4">
            <Card className="w-full max-w-sm">
                <CardHeader>
                    <CardTitle className="text-2xl">{t('signUp.title')}</CardTitle>
                    <CardDescription>{t('signUp.welcome')}</CardDescription>
                    <CardAction>
                        <Link to="/login" className="px-0">{t('signUp.logIn')}</Link>
                    </CardAction>
                </CardHeader>
                <CardContent className="grid gap-4">
                    <form className="grid gap-4 md:gap-6">
                        <div className="grid gap-2">
                            <Label htmlFor="firstName">{t('user.firstName')}</Label>
                            <Input id="firstName" type="text" placeholder={t('signUp.namePlaceholder')} required/>
                        </div>
                        <div className="grid gap-2">
                            <Label htmlFor="lastName">{t('user.lastName')}</Label>
                            <Input id="lastName" type="text" placeholder={t('signUp.lastNamePlaceholder')} required/>
                        </div>
                        <div className="grid gap-2">
                            <Label htmlFor="email">{t('user.email')}</Label>
                            <Input id="email" type="email" placeholder={t('signUp.emailPlaceholder')} required/>
                        </div>
                        <div className="grid gap-2">
                            <Label htmlFor="password">{t('user.password')}</Label>
                            <Input id="password" type="password" />
                        </div>
                        <div className="grid gap-2">
                            <Label htmlFor="confirmPassword">{t('signUp.confirmPassword')}</Label>
                            <Input id="confirmPassword" type="password" />
                        </div>
                        <Button type="submit" className="w-full">{t('signUp.submit')}</Button>
                    </form>
                </CardContent>
                <CardFooter>
                    <Button variant="outline" className="w-full">
                    {t('login.googleLogin')}
                    </Button>
                </CardFooter>
            </Card>
        </div>
    );
}
from django.conf.urls import patterns, url

from rango import views

urlpatterns = patterns ('', url(r'^$',views.index,name='index'),
                        url(r'^cat/(?P<category_name_url>\w+)', views.category, name='category'),
                        url(r'^cat_add/$', views.add_category, name='add_category'),
                        url(r'^page_add/(?P<category_name_url>\w+)', views.add_page, name='add_page'),
                        url(r'^register/$', views.register, name='register'),
                        url(r'^login/$', views.user_login, name='login'),
                        url(r'restricted',views.restricted, name='restricted'),
                        url(r'^logout/$',views.user_logout, name='logout'),
)
